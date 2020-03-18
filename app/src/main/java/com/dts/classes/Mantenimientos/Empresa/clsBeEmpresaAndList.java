package com.dts.classes.Mantenimientos.Empresa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeEmpresaAndList {
    @ElementList(inline=true,required = false)
    public List<clsBeEmpresaAnd> items = new List<clsBeEmpresaAnd>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeEmpresaAnd> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeEmpresaAnd clsBeEmpresaAnd) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeEmpresaAnd> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeEmpresaAnd> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeEmpresaAnd get(int index) {
            return null;
        }

        @Override
        public clsBeEmpresaAnd set(int index, clsBeEmpresaAnd element) {
            return null;
        }

        @Override
        public void add(int index, clsBeEmpresaAnd element) {

        }

        @Override
        public clsBeEmpresaAnd remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeEmpresaAnd> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeEmpresaAnd> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeEmpresaAnd> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}
