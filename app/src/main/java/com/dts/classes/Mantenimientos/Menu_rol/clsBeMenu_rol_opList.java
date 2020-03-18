package com.dts.classes.Mantenimientos.Menu_rol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeMenu_rol_opList {
    @ElementList(inline=true,required=false)
    public List<clsBeMenu_rol_op> items = new List<clsBeMenu_rol_op>() {
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
        public Iterator<clsBeMenu_rol_op> iterator() {
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
        public boolean add(clsBeMenu_rol_op clsBeMenu_rol_op) {
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
        public boolean addAll(@NonNull Collection<? extends clsBeMenu_rol_op> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeMenu_rol_op> c) {
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
        public clsBeMenu_rol_op get(int index) {
            return null;
        }

        @Override
        public clsBeMenu_rol_op set(int index, clsBeMenu_rol_op element) {
            return null;
        }

        @Override
        public void add(int index, clsBeMenu_rol_op element) {

        }

        @Override
        public clsBeMenu_rol_op remove(int index) {
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
        public ListIterator<clsBeMenu_rol_op> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeMenu_rol_op> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeMenu_rol_op> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}